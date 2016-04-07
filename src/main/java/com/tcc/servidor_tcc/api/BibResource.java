package com.tcc.servidor_tcc.api;

import com.tcc.servidor_tcc.entidades.BibFile;
import com.tcc.servidor_tcc.entidades.Study;
import org.jbibtex.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.*;

@Path("/bib")
public class BibResource {

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response parseBib(byte[] bibstream){
        BibFile bib = new BibFile();
        List<Study> studies = new ArrayList<>();
        Reader reader = new InputStreamReader(new ByteArrayInputStream(bibstream));
        try {
            CharacterFilterReader filterReader = new CharacterFilterReader(reader){
                @Override
                protected boolean accept(char c) {
                    if(c=='['||c==']') return false;
                    return super.accept(c);
                }
            };
            BibTeXParser bibTeXParser = new BibTeXParser();
            BibTeXDatabase database = bibTeXParser.parse(filterReader);
            Map<Key,BibTeXEntry> entryMap = database.getEntries();
            Collection<BibTeXEntry> entries =entryMap.values();
            for(BibTeXEntry entry: entries){
                Study study = new Study();
                Value title = entry.getField(BibTeXEntry.KEY_TITLE);
                if(title==null){
                    continue;
                }else{
                    study.setTitle(title.toUserString());
                }
                Value author = entry.getField(BibTeXEntry.KEY_AUTHOR);
                if(author==null){
                    continue;
                }else {
                    List<String> authors = Arrays.asList(author.toUserString().split(","));
                    study.setAuthors(authors);
                }
                Value studyabstract = entry.getField(new Key("abstract"));
                if(studyabstract==null){
                    continue;
                }else {
                    study.setStudyAbstract(studyabstract.toUserString());
                }
                studies.add(study);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bib.setStudies(studies);
        return Response.ok().entity(bib).build();
    }
}
