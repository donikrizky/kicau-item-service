package com.donikrizky.kicau.itemservice.config.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		String username = "system";

		return Optional.of(username);
	}

}