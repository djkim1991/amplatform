package io.anymobi.domain.dto.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Package : io.anymobi.domain.dto.cache
 * Developer Team : Anymobi System Development Division
 * Date : 2019-03-02
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    private static final long serialVersionUID = -339516038496531943L;
    private String sno;
    private String name;
    private String sex;
}
