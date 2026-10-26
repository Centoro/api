package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "isp_ports")
public class IspPort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "port_id", unique = true, nullable = false, length = 50)
    private String portId;
    
    @Column(name = "service_name", length = 255)
    private String serviceName;
    
    @Column(nullable = false, length = 20)
    private String port;
    
    @Column(name = "port_range", length = 50)
    private String portRange;
    
    @Column(length = 20)
    private String protocol; // UDP, TCP, TLS
    
    @Column(length = 20)
    private String direction; // Inbound, Outbound, Both
    
    @Column(length = 255)
    private String target;
    
    @Column(length = 50)
    private String status = "open";
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPortId() { return portId; }
    public void setPortId(String portId) { this.portId = portId; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getPort() { return port; }
    public void setPort(String port) { this.port = port; }
    public String getPortRange() { return portRange; }
    public void setPortRange(String portRange) { this.portRange = portRange; }
    public String getProtocol() { return protocol; }
    public void setProtocol(String protocol) { this.protocol = protocol; }
    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}