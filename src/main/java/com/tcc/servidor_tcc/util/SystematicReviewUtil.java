package com.tcc.servidor_tcc.util;

import com.tcc.servidor_tcc.entidades.*;
import com.tcc.servidor_tcc.type.PaperDivisionType;
import com.tcc.servidor_tcc.type.RoleType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pantera on 19/03/16.
 */
public class SystematicReviewUtil {

    private void separateStudies(List<ReviewerRole> participatingReviewers, PaperDivisionType divisionType, BibFile bib){
        List<Reviewer> selectionReviewers = participatingReviewers
                .stream()
                .filter( r -> r.getRoles().contains(RoleType.SELECTION))
                .map( r -> r.getReviewer())
                .collect(Collectors.toList());

        switch(divisionType){
            case ALL:
                for(Study s:bib.getStudies()){
                    for(Reviewer r: selectionReviewers){
                        ReviewedStudy reviewedStudy = new ReviewedStudy();
                        reviewedStudy.setReviewer(r);
                        reviewedStudy.setStudy(s);
                        s.addReviewedStudy(reviewedStudy);
                    }
                }
                break;
            case SPLIT:
                List<Study> studies = bib.getStudies();
                for(int i=0;i<studies.size();i++){
                    ReviewedStudy reviewedStudy = new ReviewedStudy();
                    reviewedStudy.setReviewer(selectionReviewers.get(i % selectionReviewers.size()));
                    reviewedStudy.setStudy(studies.get(i));
                    studies.get(i).addReviewedStudy(reviewedStudy);
                }
                break;
        }
    }
}
