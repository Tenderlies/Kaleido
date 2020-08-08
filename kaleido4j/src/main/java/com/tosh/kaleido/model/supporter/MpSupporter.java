package com.tosh.kaleido.model.supporter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class MpSupporter {
	private String userName;
	private String dateTime;
	private boolean publish;
}
