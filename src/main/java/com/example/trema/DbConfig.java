package com.example.trema;


import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Configuration
public class DbConfig{
    @Value("vector_db.json")
    private String vectorDb;

    @Value("classpath:/docs/dataab.txt")
    Resource faqResource;


    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel)throws IOException{
        SimpleVectorStore vectorStore= SimpleVectorStore.builder(embeddingModel).build();
        File vectorFile=getVectorFile();
        if(vectorFile.exists()){
            vectorStore.load(vectorFile);
        }else{
            TextReader reader=new TextReader(faqResource);
            List<Document> documents=reader.get();
            TextSplitter textSplitter=new TokenTextSplitter();
            List<Document>splitDocuments=textSplitter.apply(documents);
            vectorStore.add(splitDocuments);
            vectorStore.save(vectorFile);
        }
        return vectorStore;
    }

    private File getVectorFile() {
        Path path= Paths.get("src","main","resources","docs");
        String absolutePath=path.toFile().getAbsolutePath()+"/"+vectorDb;
        return new File(absolutePath);
    }


}
