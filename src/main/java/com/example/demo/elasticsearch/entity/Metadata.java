package com.example.demo.elasticsearch.entity;

import java.sql.Timestamp;


public class Metadata {

	@Override
	public String toString() {
		return "Metadata{" +
				"documentId='" + documentId + '\'' +
				", neid=" + neid +
				", nsid=" + nsid +
				", journalId=" + journalId +
				", path='" + path + '\'' +
				", filename='" + filename + '\'' +
				", desc='" + desc + '\'' +
				", tags='" + tags + '\'' +
				", pathDepth=" + pathDepth +
				", type=" + type +
				", status=" + status +
				", file_type=" + file_type +
				", extension='" + extension + '\'' +
				", content='" + content + '\'' +
				", accountId=" + accountId +
				", creator_uid=" + creator_uid +
				", updator_uid=" + updator_uid +
				", bytes=" + bytes +
				", revision='" + revision + '\'' +
				", hash='" + hash + '\'' +
				", rev_index=" + rev_index +
				", utime=" + utime +
				", mtime=" + mtime +
				", version=" + version +
				", url='" + url + '\'' +
				", isTeam=" + isTeam +
				", isLocal=" + isLocal +
				", folderDcType=" + folderDcType +
				", regionId=" + regionId +
				'}';
	}

	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public static String getTypeName() {
		return typeName;
	}

	public static String getIndexPrefix() {
		return indexPrefix;
	}

	public static String getRegionPrefix() {
		return regionPrefix;
	}

	String documentId;

	Long neid;
   
	Long nsid;
	
	Long journalId;
	
	String path;
    
    String filename;

    String desc;
    
    String tags;
    
    int pathDepth;
    
    int type;
    
    int status;
    
    int file_type;
    
    String extension;
    
    String content;

	Long accountId;
    
    Long creator_uid;
    
    Long updator_uid;
    
    Long bytes;
    
    String revision;
    
    String hash;
    
    Long rev_index; 
    
    Timestamp utime;
    
    Timestamp mtime;
    
    Long version;
    
    String url;
    
    boolean isTeam;
    
    boolean isLocal;
    
    int folderDcType;
    
    long regionId=0;
    
    public long getRegionId() {
		return regionId;
	}
	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}
	public boolean isTeam() {
		return isTeam;
	}
	public boolean isLocal() {
		return isLocal;
	}
	public int getFolderDcType() {
		return folderDcType;
	}
	public void setTeam(boolean isTeam) {
		this.isTeam = isTeam;
	}
	public void setLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}
	public void setFolderDcType(int folderDcType) {
		this.folderDcType = folderDcType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public final static String typeName = "meta"; 
    
    public final static String indexPrefix = "esearch_";
    
    public final static String regionPrefix = "localfts_";
    
    public Metadata() {
		super();
	}
    public Metadata(Long neid, Long nsid, Long journalId, String path,
                    String filename, String desc, String tags, int pathDepth, int type,
                    int status, int file_type, String extension, String content,
                    Long accountId, Long creator_uid, Long updator_uid, Long bytes,
                    String revision, String hash, Long rev_index, Timestamp utime, Timestamp mtime,
                    boolean isTeam, boolean isLocal, int folderDcType, long regionId) {
		super();
		this.neid = neid;
		this.nsid = nsid;
		this.journalId = journalId;
		this.path = path;
		this.filename = filename;
		this.desc = desc;
		this.tags = tags;
		this.pathDepth = pathDepth;
		this.type = type;
		this.status = status;
		this.file_type = file_type;
		this.extension = extension;
		this.content = content;
		this.accountId = accountId;
		this.creator_uid = creator_uid;
		this.updator_uid = updator_uid;
		this.bytes = bytes;
		this.revision = revision;
		this.hash = hash==null?"":hash;
		this.rev_index = rev_index;
		this.utime = utime;
		this.mtime = mtime;
		this.isTeam = isTeam;
		this.isLocal = isLocal;
		this.folderDcType = folderDcType;
		this.regionId = regionId;
	}
    
    public Long getJournalId() {
		return journalId;
	}

	public String getDesc() {
		return desc;
	}

	public String getTags() {
		return tags;
	}

	public void setJournalId(Long journalId) {
		this.journalId = journalId;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Long getNeid() {
		return neid;
	}

	public Long getNsid() {
		return nsid;
	}

	public String getPath() {
		return path;
	}

	public String getFilename() {
		return filename;
	}

	public int getPathDepth() {
		return pathDepth;
	}

	public int getType() {
		return type;
	}

	public int getStatus() {
		return status;
	}

	public int getFile_type() {
		return file_type;
	}

	public String getExtension() {
		return extension;
	}

	public Long getAccountId() {
		return accountId;
	}

	public Long getCreator_uid() {
		return creator_uid;
	}

	public Long getUpdator_uid() {
		return updator_uid;
	}

	public Long getBytes() {
		return bytes;
	}

	public String getRevision() {
		return revision;
	}

	public String getHash() {
		return hash;
	}

	public Long getRev_index() {
		return rev_index;
	}

	public Timestamp getUtime() {
		return utime;
	}

	public Timestamp getMtime() {
		return mtime;
	}

	public void setNeid(Long neid) {
		this.neid = neid;
	}

	public void setNsid(Long nsid) {
		this.nsid = nsid;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setPathDepth(int pathDepth) {
		this.pathDepth = pathDepth;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public void setCreator_uid(Long creator_uid) {
		this.creator_uid = creator_uid;
	}

	public void setUpdator_uid(Long updator_uid) {
		this.updator_uid = updator_uid;
	}

	public void setBytes(Long bytes) {
		this.bytes = bytes;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setRev_index(Long rev_index) {
		this.rev_index = rev_index;
	}

	public void setUtime(Timestamp utime) {
		this.utime = utime;
	}

	public void setMtime(Timestamp mtime) {
		this.mtime = mtime;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

