package org.earlspilner.articles.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alexander Dudkin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRequest {
    private String name;
}
