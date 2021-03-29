package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQSectionsItemsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQSectionsItems.class);
        RFQSectionsItems rFQSectionsItems1 = new RFQSectionsItems();
        rFQSectionsItems1.setId(1L);
        RFQSectionsItems rFQSectionsItems2 = new RFQSectionsItems();
        rFQSectionsItems2.setId(rFQSectionsItems1.getId());
        assertThat(rFQSectionsItems1).isEqualTo(rFQSectionsItems2);
        rFQSectionsItems2.setId(2L);
        assertThat(rFQSectionsItems1).isNotEqualTo(rFQSectionsItems2);
        rFQSectionsItems1.setId(null);
        assertThat(rFQSectionsItems1).isNotEqualTo(rFQSectionsItems2);
    }
}
