package com.eitan.msexpression.allocation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Allocation {
  @Id @GeneratedValue
  private long id;

  private long projectId;
  private long userId;
  private LocalDate start, end;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Allocation that = (Allocation) o;
    return id == that.id &&
        projectId == that.projectId &&
        userId == that.userId &&
        Objects.equals(start, that.start) &&
        Objects.equals(end, that.end);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, projectId, userId, start, end);
  }

  @Override
  public String toString() {
    return "Allocation{" +
        "id=" + id +
        ", projectId=" + projectId +
        ", userId=" + userId +
        ", start=" + start +
        ", end=" + end +
        '}';
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public LocalDate getStart() {
    return start;
  }

  public void setStart(LocalDate start) {
    this.start = start;
  }

  public LocalDate getEnd() {
    return end;
  }

  public void setEnd(LocalDate end) {
    this.end = end;
  }

  public Allocation() {
  }
}
