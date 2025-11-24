package app.examples;

import app.io.HttpImageClient;
import app.io.ImageRequest;
import app.io.ImageResponse;
import app.io.ImageResponseCoder;
import autovalue.shaded.com.google.common.collect.ImmutableList;
import org.apache.beam.io.requestresponse.ApiIOError;
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

import java.time.Instant;
import java.util.List;
import java.util.Objects;


public class HttpResponseRequest implements Examples {

    public void build(Pipeline p)  {
        final Logger LOG = LoggerFactory.getLogger(HttpResponseRequest.class);
        List<String> urls = ImmutableList.of(
                "https://storage.googleapis.com/generativeai-downloads/images/cake.jpg",
                "https://storage.googleapis.com/generativeai-downloads/images/chocolate.png",
                "https://storage.googleapis.com/generativeai-downloads/images/croissant.jpg",
                "https://storage.googleapis.com/generativeai-downloads/images/dog_form.jpg",
                "https://storage.googleapis.com/generativeai-downloads/images/factory.png",
                "https://storage.googleapis.com/generativeai-downloads/images/scones.jpg"
        );

        PCollection<KV<String, ImageRequest>> requests = p
                .apply(Create.of(urls))
                .apply("toImageRequest", MapElements.via(
                        new SimpleFunction<>() {
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
        result.getFailures()
                .apply("LogErrors", ParDo.of(new DoFn<ApiIOError, Void>() {
                    @ProcessElement
                    public void processElement(ProcessContext c) {
                        LOG.error("{} Failed to fetch image: {}",
                                Instant.now().toString(),
                                Objects.requireNonNull(c.element()).getMessage());
                    }
                }));

        result.getResponses()
                .apply("LogResults", ParDo.of(new DoFn<KV<String, ImageResponse>, Void>() {
                    @ProcessElement
                    public void processElement(ProcessContext c) {
                        String url = Objects.requireNonNull(c.element()).getKey();
                        ImageResponse response = Objects.requireNonNull(c.element()).getValue();
                        assert response != null;
                        LOG.info("{} Fetched image: {} with size: {}",
                                Instant.now().toString(),
                                url,
                                response.getData().size()
                                );
                    }
                }));
    }
}
