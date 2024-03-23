package app.examples;

import app.io.HttpImageClient;
import app.io.ImageRequest;
import app.io.ImageResponse;
import app.io.ImageResponseCoder;
import autovalue.shaded.com.google.common.collect.ImmutableList;
import org.apache.beam.io.requestresponse.RequestResponseIO;
import org.apache.beam.io.requestresponse.Result;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class HttpResponseRequest {
    private static final Logger LOG = LoggerFactory.getLogger(HttpResponseRequest.class);
    public static void main(String[] args) {
        List<String> urls = ImmutableList.of(
                "https://storage.googleapis.com/generativeai-downloads/images/cake.jpg"
        );

        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline p = Pipeline.create(options);

        PCollection<KV<String, ImageRequest>> requests = p
                .apply(Create.of(urls))
                .apply("toImageRequest", MapElements.via(
                        new SimpleFunction<String, KV<String, ImageRequest>>() {
                            @Override
                            public KV<String, ImageRequest> apply(String url) {
                                return KV.of(
                                        url,
                                        ImageRequest.of(url));
                            }
                }));

        KvCoder<String, ImageResponse> responseCoder = KvCoder.of(
                StringUtf8Coder.of(), ImageResponseCoder.of());

        Result<KV<String, ImageResponse>> result =
                requests.apply(
                        ImageResponse.class.getSimpleName(),
                        RequestResponseIO.of(
                                HttpImageClient.of(),
                                responseCoder
                        )
                );
        /** ToDO Logging
         * result.getFailures().xxx
         */

        p.run().waitUntilFinish();
    }
}
