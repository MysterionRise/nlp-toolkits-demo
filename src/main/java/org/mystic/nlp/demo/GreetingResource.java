package org.mystic.nlp.demo;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws IOException {
        InputStream chunkerModelIn = new FileInputStream("models/en-chunker.bin");
        ChunkerModel chunkerModel = new ChunkerModel(chunkerModelIn);
        ChunkerME chunker = new ChunkerME(chunkerModel);

        InputStream posTaggerIn = new FileInputStream("models/en-pos-maxent.bin");
        POSModel posModel = new POSModel(posTaggerIn);
        POSTaggerME tagger = new POSTaggerME(posModel);

        String sent[] = new String[]{"What", "is", "outcome", "of", "Bernie", "Madoff", "and", "his", "Ponzi", "Scheme"};

        String tags[] = tagger.tag(sent);
        String chunkTags[] = chunker.chunk(sent, tags);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < chunkTags.length; ++i) {
            result.append(sent[i] + " " + tags[i] + " " + chunkTags[i]);
            result.append("\n");
        }

        return result.toString();
    }
}
