package com.chakray.microservice.pojo;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Instruction {

  private Integer serviceId;
  private String description;
  private LocalDateTime date;
  private String destination;
  private Information information;

}
