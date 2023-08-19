package com.zenith.via.handler;

import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import lombok.NonNull;

import java.lang.reflect.Method;

public class MCProxyViaChannelInitializer extends ChannelInitializer<Channel> {
    private final ChannelInitializer<Channel> original;

    public MCProxyViaChannelInitializer(ChannelInitializer<Channel> original) {
        this.original = original;
    }

    @Override
    protected void initChannel(@NonNull Channel channel) throws Exception {
        Method initChannelMethod = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
        initChannelMethod.setAccessible(true);
        initChannelMethod.invoke(original, channel);

        UserConnectionImpl userConnection = new UserConnectionImpl(channel, true);
        new ProtocolPipelineImpl(userConnection);
        // outbound order before: manager -> codec -> compression -> sizer -> encryption -> readTimeout
        // outbound order after: manager -> via-encoder -> codec -> compression -> sizer -> encryption -> readTimeout

        // inbound order before: readTimeout -> encryption -> sizer -> compression -> codec -> manager
        // inbound order after: readTimeout -> encryption -> sizer -> compression -> via-decoder -> codec -> manager

        // pipeline order before readTimeout -> encryption -> sizer -> compression -> codec -> manager
        // pipeline order after readTimeout -> encryption -> sizer -> compression -> via-encoder -> via-decoder -> codec -> manager
        channel.pipeline().addBefore("codec", "via-encoder", new MCProxyViaEncodeHandler(userConnection));
        channel.pipeline().addBefore("codec", "via-decoder", new MCProxyViaDecodeHandler(userConnection));
    }
}
