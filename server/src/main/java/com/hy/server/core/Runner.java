package com.hy.server.core;

import com.hy.business.context.Contexts;
import com.hy.business.annotation.NettyController;
import com.hy.business.annotation.NettyMethod;
import com.hy.business.entity.Board;
import com.hy.business.entity.GameMap;
import com.hy.business.services.IBoardService;
import com.hy.server.core.codec.HyDecoder;
import com.hy.server.core.codec.HyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class Runner implements ApplicationRunner {

    @Value("${socketPort}")
    Integer socketPort;

    Map<String, Method> handlerMap = new HashMap<String,Method>();

    @Autowired
    private HyDecoder hyDecoder;

    @Autowired
    private HyEncoder hyEncoder;

    @Autowired
    private IBoardService boardService;

    @Async
    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.InitContexts();//Init Contexts
        this.InitController();//初始化所有自定义Controller及method
        this.StartServer();//启动服务
    }

    private void InitContexts() throws IOException {
        //Step 1 init system board;
        List<Board> boards = boardService.GetBoards();
        Contexts.boardLists = boards;

        //Step 2 init all maps
        GameMap gm = new GameMap();
        gm.setMaxX(18000);
        gm.setMaxY(11000);
        gm.setMapName("城地圧");
        gm.setMapId(1601);
        Contexts.maps.put("1601",gm);

    }

    private void InitController() {
        try {
            String BASE_PACKAGE = "com.hy.business/controller";
            String RESOURCE_PATTERN = "/**/*.class";
            System.out.println("开始扫描所有接口");
            //spring工具类，可以获取指定路径下的全部类
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            try {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                        ClassUtils.convertClassNameToResourcePath(BASE_PACKAGE) + RESOURCE_PATTERN;
                Resource[] resources = resourcePatternResolver.getResources(pattern);
                //MetadataReader 的工厂类
                MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);
                for (Resource resource : resources) {
                    //用于读取类信息
                    MetadataReader reader = readerfactory.getMetadataReader(resource);
                    //扫描到的class
                    String classname = reader.getClassMetadata().getClassName();

                    Class<?> clazz = Class.forName(classname);
                    //判断是否有指定主解
                    NettyController anno = clazz.getAnnotation(NettyController.class);
                    if (anno != null) {//找到NettyController类了
                        System.out.println(classname);
                        Method[] methods = clazz.getMethods();
                        for (Method method : methods) {
                            NettyMethod hyMethod = method.getAnnotation(NettyMethod.class);
                            if (hyMethod != null) {//找到NettyMethod接口了
                                System.out.println(hyMethod.value()[0]);
                                handlerMap.put(hyMethod.value()[0],method);
                            }
                        }


                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println("ERROR:扫描接口失败" + e.getMessage());
            } finally {
                System.out.println("扫描所有接口完成");
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }

    NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
    private void StartServer(){
        new ServerBootstrap()
                .group(nioEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new RunnerChannelInitializer() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(hyDecoder);
                        ch.pipeline().addLast(hyEncoder);
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {

                            }

                            @Override
                            public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {

                            }

                            @Override
                            public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
                                Contexts.channelGroup.add(channelHandlerContext.channel());
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                                //这是退出了   要存档呀呀
                                //dosomethings
                                Contexts.channelGroup.remove(channelHandlerContext.channel());
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {

//                                AttributeKey<String> key = AttributeKey.valueOf("user");
//                                if(!ctx.channel().hasAttr(key))
//                                    ctx.channel().attr(key).set(LocalDateTime.now().toString());
//
//                                ctx.writeAndFlush(ctx.channel().attr(key).toString()+"\r\n");
//                                System.out.println((o));
                                ByteBuf recv = (ByteBuf)o;
                                int command = (int)recv.readUnsignedByte();//取命令
                                int method = (int)recv.readUnsignedByte();//取方法
                                ByteBuf handlerRecv = recv.retainedSlice();//取其它
                                //调用命令
                                RequestHandle.doHandle(handlerMap,command,method,ctx,handlerRecv);
                                recv.release();
                            }

                            @Override
                            public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {

                            }

                            @Override
                            public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

                            }

                            @Override
                            public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {

                            }

                            @Override
                            public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

                            }

                            @Override
                            public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

                            }
                        });
                    }
                }).bind(socketPort);
    }
}
