/*
 * Copyright 2013-2018 Lilinfeng.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phei.netty.protocol.http.xml.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.StringReader;
import java.nio.charset.Charset;

import org.apache.tools.ant.filters.StringInputStream;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * @author Lilinfeng
 * @version 1.0
 * @date 2014年3月1日
 */
public abstract class AbstractHttpXmlDecoder<T> extends
        MessageToMessageDecoder<T> {
    private Class<?> clazz;
    private boolean isPrint;
    private final static String CHARSET_NAME = "UTF-8";
    private final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

    protected AbstractHttpXmlDecoder(Class<?> clazz) {
        this(clazz, false);
    }

    protected AbstractHttpXmlDecoder(Class<?> clazz, boolean isPrint) {
        this.clazz = clazz;
        this.isPrint = isPrint;
    }

    protected Object decode0(ChannelHandlerContext arg0, ByteBuf body)
            throws Exception {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            String content = body.toString(UTF_8);
            if (isPrint)
                System.out.println("The body is : " + content);
            Object object = unmarshaller.unmarshal(new StringInputStream(content));

            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to
     * forward to the next {@link ChannelHandler} in the {@link ChannelPipeline}
     * .
     * <p>
     * Sub-classes may override this method to change behavior.
     */
    @Skip
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        // 释放资源
        // super.exceptionCaught(ctx, cause);
    }
}
