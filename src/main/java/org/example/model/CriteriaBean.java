package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaBean {
    private boolean yearRangeSelected;
    private int fromYear;
    private int toYear;
    private boolean genreSelected;
    private int genre;
    private boolean directorSelected;
    private int director;

    @Override
    public String toString() {
        return "CriteriaBean [yearRangeSelected=" + yearRangeSelected
                + ", fromYear=" + fromYear + ", toYear=" + toYear
                + ", genreSelected=" + genreSelected + ", genre=" + genre
                + ", directorSelected=" + directorSelected + ", director="
                + director + "]";
    }

}