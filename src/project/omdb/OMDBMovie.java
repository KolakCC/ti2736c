
package project.omdb;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class OMDBMovie {
    private int ourID;

    public int getOurID() {
        return ourID;
    }

    public void setOurID(int ourID) {
        this.ourID = ourID;
    }

    @Expose
    private String Title;
    @Expose
    private String Year;
    @Expose
    private String Rated;
    @Expose
    private String Released;
    @Expose
    private String Director;
    @Expose
    private String Writer;
    @Expose
    private String Actors;
    @Expose
    private String Language;
    @Expose
    private String Metascore;
    @Expose
    private String imdbRating;
    @Expose
    private String Genre;

    public String[] getGenreArray() {
        return getGenre().split(", ");
    }

    public String[] getDirectorArray() {
        return getDirector().split(", ");
    }

    /**
     * @return The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * @return The Year
     */
    public String getYear() {
        return Year;
    }

    /**
     * @param Year The Year
     */
    public void setYear(String Year) {
        this.Year = Year;
    }

    /**
     * @return The Rated
     */
    public String getRated() {
        return Rated;
    }

    /**
     * @param Rated The Rated
     */
    public void setRated(String Rated) {
        this.Rated = Rated;
    }

    /**
     * @return The Released
     */
    public String getReleased() {
        return Released;
    }

    /**
     * @param Released The Released
     */
    public void setReleased(String Released) {
        this.Released = Released;
    }

    /**
     * @return The Director
     */
    public String getDirector() {
        return Director;
    }

    /**
     * @param Director The Director
     */
    public void setDirector(String Director) {
        this.Director = Director;
    }

    /**
     * @return The Writer
     */
    public String getWriter() {
        return Writer;
    }

    /**
     * @param Writer The Writer
     */
    public void setWriter(String Writer) {
        this.Writer = Writer;
    }

    /**
     * @return The Actors
     */
    public String getActors() {
        return Actors;
    }

    /**
     * @param Actors The Actors
     */
    public void setActors(String Actors) {
        this.Actors = Actors;
    }


    /**
     * @return The Language
     */
    public String getLanguage() {
        return Language;
    }

    /**
     * @param Language The Language
     */
    public void setLanguage(String Language) {
        this.Language = Language;
    }

    /**
     * @return The Metascore
     */
    public String getMetascore() {
        return Metascore;
    }

    /**
     * @param Metascore The Metascore
     */
    public void setMetascore(String Metascore) {
        this.Metascore = Metascore;
    }

    /**
     * @return The imdbRating
     */
    public String getImdbRating() {
        return imdbRating;
    }

    /**
     * @param imdbRating The imdbRating
     */
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
}