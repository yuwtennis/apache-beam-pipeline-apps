package app.io;

import autovalue.shaded.com.google.common.collect.ImmutableMap;
import com.google.auto.value.AutoValue;
import org.apache.beam.sdk.schemas.AutoValueSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.commons.compress.utils.FileNameUtils;

import java.io.Serializable;
import java.util.Map;

@DefaultSchema(AutoValueSchema.class)
@AutoValue
public abstract class  ImageRequest implements Serializable {
    static final TypeDescriptor<ImageRequest> TYPE = TypeDescriptor.of(ImageRequest.class);
    private static final Map<String, String> EXT_MIMETYPE_MAP =
            ImmutableMap.of(
                    "jpg", "image/jpg",
                    "jpeg", "image/jpeg",
                    "png", "image/png"
            );

    /* Derive the MIME type of the image from the url based on its extension. */
    private static String mimeTypeOf(String url) {
        String ext = FileNameUtils.getExtension(url);

        if(!EXT_MIMETYPE_MAP.containsKey(ext)) {
            throw new IllegalArgumentException(
                    String.format("Could not map extension to mimetype: ext %s of url: %s", ext, url)
            );
        }
        return EXT_MIMETYPE_MAP.get(ext);
    }

    public static Builder builder() {
        return new AutoValue_ImageRequest.Builder();
    }

    /* Build an  {@link ImageRequest} from a {@param url} */
    public static ImageRequest of(String url) {
        return builder()
                .setImageUrl(url)
                .setMimeType(mimeTypeOf(url))
                .build()   ;
    }

    /** Return URL of the image request */
    abstract String getImageUrl();

    /** Return MIME type of the image request */
    abstract String getMimeType();

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setImageUrl(String value);
        abstract Builder setMimeType(String value);
        abstract ImageRequest build();
    }
}
