package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PunchTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Punch.class);
        Punch punch1 = new Punch();
        punch1.setId(1L);
        Punch punch2 = new Punch();
        punch2.setId(punch1.getId());
        assertThat(punch1).isEqualTo(punch2);
        punch2.setId(2L);
        assertThat(punch1).isNotEqualTo(punch2);
        punch1.setId(null);
        assertThat(punch1).isNotEqualTo(punch2);
    }
}
