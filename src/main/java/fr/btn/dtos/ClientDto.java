package fr.btn.dtos;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ClientDto {
    private int quota;
    private String status;
}
