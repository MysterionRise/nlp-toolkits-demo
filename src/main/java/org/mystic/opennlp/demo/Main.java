package org.mystic.opennlp.demo;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream chunkerModelIn = new FileInputStream("models/en-chunker.bin");
        ChunkerModel chunkerModel = new ChunkerModel(chunkerModelIn);
        ChunkerME chunker = new ChunkerME(chunkerModel);

        InputStream posTaggerIn = new FileInputStream("models/en-pos-maxent.bin");
        POSModel posModel = new POSModel(posTaggerIn);
        POSTaggerME tagger = new POSTaggerME(posModel);

        String sent[] = new String[] { "What", "is", "outcome", "of", "Bernie", "Madoff", "and", "his", "Ponzi", "Scheme" };

        String tags[] = tagger.tag(sent);
        String chunkTags[] = chunker.chunk(sent, tags);
        for (int i = 0; i < chunkTags.length; ++i) {
            System.out.println(sent[i] + " " + tags[i] + " " + chunkTags[i]);
        }
    }
}
