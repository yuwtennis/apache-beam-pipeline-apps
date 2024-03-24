package app.io;

import com.google.protobuf.ByteString;
import org.apache.beam.sdk.coders.*;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** A {@link CustomCoder} of an {@link ImageResponse} */
public class ImageResponseCoder extends CustomCoder<ImageResponse> {
    public static ImageResponseCoder of() {
        return new ImageResponseCoder();
    }

    private static final Coder<byte[]> BYTE_ARRAY_CODER = ByteArrayCoder.of();
    private static final Coder<String> STRING_CODER = StringUtf8Coder.of();

    @Override
    public void encode(ImageResponse value, OutputStream outputStream)
            throws CoderException, IOException {
        BYTE_ARRAY_CODER.encode(value.getData().toByteArray(), outputStream);
        STRING_CODER.encode(value.getMimeType(), outputStream);
    }

    @Override
    public ImageResponse decode(InputStream inputStream) throws CoderException, IOException {
        byte[] data = BYTE_ARRAY_CODER.decode(inputStream);
        String mimeType = STRING_CODER.decode(inputStream);

        return ImageResponse
                .builder()
                .setData(ByteString.copyFrom(data))
                .setMimeType(mimeType).build();
    }
}
