package me.tungexplorer.james_generate_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JmapBaseRequest {
    protected Set<String> using = new HashSet<>();
}
