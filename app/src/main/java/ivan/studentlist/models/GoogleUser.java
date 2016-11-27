package ivan.studentlist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GoogleUser {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("objectType")
    @Expose
    private String objectType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("organizations")
    @Expose
    private List<Organization> organizations = new ArrayList<Organization>();
    @SerializedName("placesLived")
    @Expose
    private List<PlacesLived> placesLived = new ArrayList<PlacesLived>();
    @SerializedName("isPlusUser")
    @Expose
    private Boolean isPlusUser;
    @SerializedName("circledByCount")
    @Expose
    private Integer circledByCount;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("cover")
    @Expose
    private Cover cover;

    /**
     *
     * @return
     *     The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     *     The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     *
     * @return
     *     The etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     *
     * @param etag
     *     The etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     *
     * @return
     *     The birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     *
     * @param birthday
     *     The birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     *
     * @return
     *     The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     *     The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     *     The objectType
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     *
     * @param objectType
     *     The objectType
     */
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    /**
     *
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName
     *     The displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     *
     * @return
     *     The name
     */
    public Name getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     *     The image
     */
    public Image getImage() {
        return image;
    }

    /**
     *
     * @param image
     *     The image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     *
     * @return
     *     The organizations
     */
    public List<Organization> getOrganizations() {
        return organizations;
    }

    /**
     *
     * @param organizations
     *     The organizations
     */
    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    /**
     *
     * @return
     *     The placesLived
     */
    public List<PlacesLived> getPlacesLived() {
        return placesLived;
    }

    /**
     *
     * @param placesLived
     *     The placesLived
     */
    public void setPlacesLived(List<PlacesLived> placesLived) {
        this.placesLived = placesLived;
    }

    /**
     *
     * @return
     *     The isPlusUser
     */
    public Boolean getIsPlusUser() {
        return isPlusUser;
    }

    /**
     *
     * @param isPlusUser
     *     The isPlusUser
     */
    public void setIsPlusUser(Boolean isPlusUser) {
        this.isPlusUser = isPlusUser;
    }

    /**
     *
     * @return
     *     The circledByCount
     */
    public Integer getCircledByCount() {
        return circledByCount;
    }

    /**
     *
     * @param circledByCount
     *     The circledByCount
     */
    public void setCircledByCount(Integer circledByCount) {
        this.circledByCount = circledByCount;
    }

    /**
     *
     * @return
     *     The verified
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     *
     * @param verified
     *     The verified
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    /**
     *
     * @return
     *     The cover
     */
    public Cover getCover() {
        return cover;
    }

    /**
     *
     * @param cover
     *     The cover
     */
    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public class Cover {

        @SerializedName("layout")
        @Expose
        private String layout;
        @SerializedName("coverPhoto")
        @Expose
        private CoverPhoto coverPhoto;
        @SerializedName("coverInfo")
        @Expose
        private CoverInfo coverInfo;

        /**
         *
         * @return
         *     The layout
         */
        public String getLayout() {
            return layout;
        }

        /**
         *
         * @param layout
         *     The layout
         */
        public void setLayout(String layout) {
            this.layout = layout;
        }

        /**
         *
         * @return
         *     The coverPhoto
         */
        public CoverPhoto getCoverPhoto() {
            return coverPhoto;
        }

        /**
         *
         * @param coverPhoto
         *     The coverPhoto
         */
        public void setCoverPhoto(CoverPhoto coverPhoto) {
            this.coverPhoto = coverPhoto;
        }

        /**
         *
         * @return
         *     The coverInfo
         */
        public CoverInfo getCoverInfo() {
            return coverInfo;
        }

        /**
         *
         * @param coverInfo
         *     The coverInfo
         */
        public void setCoverInfo(CoverInfo coverInfo) {
            this.coverInfo = coverInfo;
        }

    }

    public class CoverInfo {

        @SerializedName("topImageOffset")
        @Expose
        private Integer topImageOffset;
        @SerializedName("leftImageOffset")
        @Expose
        private Integer leftImageOffset;

        /**
         *
         * @return
         *     The topImageOffset
         */
        public Integer getTopImageOffset() {
            return topImageOffset;
        }

        /**
         *
         * @param topImageOffset
         *     The topImageOffset
         */
        public void setTopImageOffset(Integer topImageOffset) {
            this.topImageOffset = topImageOffset;
        }

        /**
         *
         * @return
         *     The leftImageOffset
         */
        public Integer getLeftImageOffset() {
            return leftImageOffset;
        }

        /**
         *
         * @param leftImageOffset
         *     The leftImageOffset
         */
        public void setLeftImageOffset(Integer leftImageOffset) {
            this.leftImageOffset = leftImageOffset;
        }

    }

    public class CoverPhoto {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("height")
        @Expose
        private Integer height;
        @SerializedName("width")
        @Expose
        private Integer width;

        /**
         *
         * @return
         *     The url
         */
        public String getUrl() {
            return url;
        }

        /**
         *
         * @param url
         *     The url
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         *
         * @return
         *     The height
         */
        public Integer getHeight() {
            return height;
        }

        /**
         *
         * @param height
         *     The height
         */
        public void setHeight(Integer height) {
            this.height = height;
        }

        /**
         *
         * @return
         *     The width
         */
        public Integer getWidth() {
            return width;
        }

        /**
         *
         * @param width
         *     The width
         */
        public void setWidth(Integer width) {
            this.width = width;
        }

    }

    public class Image {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("isDefault")
        @Expose
        private Boolean isDefault;

        /**
         *
         * @return
         *     The url
         */
        public String getUrl() {
            return url;
        }

        /**
         *
         * @param url
         *     The url
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         *
         * @return
         *     The isDefault
         */
        public Boolean getIsDefault() {
            return isDefault;
        }

        /**
         *
         * @param isDefault
         *     The isDefault
         */
        public void setIsDefault(Boolean isDefault) {
            this.isDefault = isDefault;
        }

    }

    public class Name {

        @SerializedName("familyName")
        @Expose
        private String familyName;
        @SerializedName("givenName")
        @Expose
        private String givenName;

        /**
         *
         * @return
         *     The familyName
         */
        public String getFamilyName() {
            return familyName;
        }

        /**
         *
         * @param familyName
         *     The familyName
         */
        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        /**
         *
         * @return
         *     The givenName
         */
        public String getGivenName() {
            return givenName;
        }

        /**
         *
         * @param givenName
         *     The givenName
         */
        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

    }

    public class Organization {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("startDate")
        @Expose
        private String startDate;
        @SerializedName("endDate")
        @Expose
        private String endDate;
        @SerializedName("primary")
        @Expose
        private Boolean primary;

        /**
         *
         * @return
         *     The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         *     The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         *
         * @return
         *     The title
         */
        public String getTitle() {
            return title;
        }

        /**
         *
         * @param title
         *     The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         *
         * @return
         *     The type
         */
        public String getType() {
            return type;
        }

        /**
         *
         * @param type
         *     The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         *
         * @return
         *     The startDate
         */
        public String getStartDate() {
            return startDate;
        }

        /**
         *
         * @param startDate
         *     The startDate
         */
        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        /**
         *
         * @return
         *     The endDate
         */
        public String getEndDate() {
            return endDate;
        }

        /**
         *
         * @param endDate
         *     The endDate
         */
        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        /**
         *
         * @return
         *     The primary
         */
        public Boolean getPrimary() {
            return primary;
        }

        /**
         *
         * @param primary
         *     The primary
         */
        public void setPrimary(Boolean primary) {
            this.primary = primary;
        }

    }

    public class PlacesLived {

        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("primary")
        @Expose
        private Boolean primary;

        /**
         *
         * @return
         *     The value
         */
        public String getValue() {
            return value;
        }

        /**
         *
         * @param value
         *     The value
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         *
         * @return
         *     The primary
         */
        public Boolean getPrimary() {
            return primary;
        }

        /**
         *
         * @param primary
         *     The primary
         */
        public void setPrimary(Boolean primary) {
            this.primary = primary;
        }

    }

}