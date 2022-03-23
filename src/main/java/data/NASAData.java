package data;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;

public class NASAData {
    private String copyright;
    private LocalDate date;
    private String explanation;
    private URL hdurl;
    private String media_type;
    private String service_version;
    private String title;
    private URL url;

    public NASAData() {
    }

    public NASAData(String copyright,
                    LocalDate date,
                    String explanation,
                    URL hdurl,
                    String media_type,
                    String service_version,
                    String title,
                    URL url) {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.media_type = media_type;
        this.service_version = service_version;
        this.title = title;
        this.url = url;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setHdurl(URL hdurl) {
        this.hdurl = hdurl;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public void setService_version(String service_version) {
        this.service_version = service_version;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getCopyright() {
        return copyright;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public URL getHdurl() {
        return hdurl;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getService_version() {
        return service_version;
    }

    public String getTitle() {
        return title;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "NASAData{" +
                "copyright='" + copyright + '\'' +
                ", date=" + date +
                ", explanation='" + explanation + '\'' +
                ", hdurl=" + hdurl +
                ", media_type='" + media_type + '\'' +
                ", service_version='" + service_version + '\'' +
                ", title='" + title + '\'' +
                ", url=" + url +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NASAData data = (NASAData) o;
        return Objects.equals(copyright, data.copyright)
                && Objects.equals(date, data.date)
                && Objects.equals(explanation, data.explanation)
                && Objects.equals(hdurl, data.hdurl)
                && Objects.equals(media_type, data.media_type)
                && Objects.equals(service_version, data.service_version)
                && Objects.equals(title, data.title)
                && Objects.equals(url, data.url);
    }
}
