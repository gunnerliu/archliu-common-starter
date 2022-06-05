package cn.archliu.common.response.sub;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class OptionsData {

    private List<Option> options;

    @Data
    @Accessors(chain = true)
    public static class Option {

        private String label;

        private String value;

    }

}
