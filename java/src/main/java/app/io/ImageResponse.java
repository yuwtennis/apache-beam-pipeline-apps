package app.io;

import com.google.auto.value.AutoValue;
import com.google.protobuf.ByteString;
import org.apache.beam.sdk.schemas.AutoValueSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.io.Serializable;

@DefaultSchema(AutoValueSchema.class)
@AutoValue
public abstract class ImageResponse implements Serializable {
    static Builder builder() {
        return new AutoValue_ImageResponse.Builder();
    }

    public abstract String getMimeType();

    public abstract ByteString getData();

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setMimeType(String value);
        abstract Builder setData(ByteString value);
        abstract ImageResponse build();

    }
}
