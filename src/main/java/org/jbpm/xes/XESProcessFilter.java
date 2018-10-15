package org.jbpm.xes;

import java.util.Date;
import java.util.List;

public class XESProcessFilter {

    private String processId;

    private String processVersion;

    private Date since;

    private Date to;

    private List<Integer> status;

    private Boolean allNodeTypes = Boolean.FALSE;

    private Integer nodeInstanceLogType;

    public String getProcessId() {
        return processId;
    }

    public String getProcessVersion() {
        return processVersion;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public void setProcessVersion(String processVersion) {
        this.processVersion = processVersion;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public void setAllNodeTypes(Boolean allNodeTypes) {
        this.allNodeTypes = allNodeTypes;
    }

    public Boolean isAllNodeTypes() {
        return allNodeTypes;
    }

    public void setNodeInstanceLogType(Integer nodeInstanceLogType) {
        this.nodeInstanceLogType = nodeInstanceLogType;
    }

    public Integer getNodeInstanceLogType() {
        return nodeInstanceLogType;
    }

    @Override
    public String toString() {
        return "XESProcessFilter{" +
                "processId='" + processId + '\'' +
                ", processVersion='" + processVersion + '\'' +
                ", since=" + since +
                ", to=" + to +
                ", status=" + status +
                ", allNodeTypes=" + allNodeTypes +
                ", nodeInstanceLogType=" + nodeInstanceLogType +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private XESProcessFilter xESProcessFilter;

        private Builder() {
            xESProcessFilter = new XESProcessFilter();
        }

        public Builder withProcessId(String processId) {
            xESProcessFilter.setProcessId(processId);
            return this;
        }

        public Builder withProcessVersion(String processVersion) {
            xESProcessFilter.setProcessVersion(processVersion);
            return this;
        }

        public Builder withSince(Date since) {
            xESProcessFilter.setSince(since);
            return this;
        }

        public Builder withTo(Date to) {
            xESProcessFilter.setTo(to);
            return this;
        }

        public Builder withStatus(List<Integer> status) {
            xESProcessFilter.setStatus(status);
            return this;
        }

        public Builder withAllNodeTypes() {
            xESProcessFilter.setAllNodeTypes(true);
            return this;
        }

        public Builder withNodeInstanceLogType(Integer type) {
            xESProcessFilter.setNodeInstanceLogType(type);
            return this;
        }

        public XESProcessFilter build() {
            return xESProcessFilter;
        }
    }
}
