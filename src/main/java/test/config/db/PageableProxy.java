package test.config.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Data;


public class PageableProxy {

	private Pageable pageable;

	public PageableProxy(Pageable page) {
		this.pageable = page;
	}

	public Pageable getPageable() {
		// TODO Auto-generated method stub
		return this.pageable;
	}

}
