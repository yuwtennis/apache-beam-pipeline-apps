package net.yuwtennis.app.pipelines;

import net.yuwtennis.app.helpers.fns.PrintFn;
import net.yuwtennis.app.schemas.StaticSchema;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;

import static net.yuwtennis.app.helpers.ios.MongoIO.Read;
import static net.yuwtennis.app.helpers.ios.MongoIO.Write;

public class MongoIOSimpleRWQueryService implements PipelineService {

    public String uri ;
    public String database ;
    public String collection ;

    public MongoIOSimpleRWQueryService() {
        // FIXME Default system env for now
        this.uri = System.getenv("MONGO_URI") ;
        this.database =  System.getenv("MONGO_DB") ;
        this.collection =  System.getenv("MONGO_COLLECTION") ;
    }

    public void run(Pipeline p) {

        PCollection pcol = p.apply(Create.of(StaticSchema.LINES)).setCoder(StringUtf8Coder.of());

        pcol = Write(pcol, uri, database, collection);
        pcol = Read(pcol, uri, database, collection);
        pcol.apply(MapElements.via(new PrintFn()));
    }
}
