package com.project.dco_common.api;

import com.project.dco_common.constants.DateTimeConstants;
import com.project.dco_common.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBody<T> {

	private HttpStatusResponse status;
	private T data;
	private String timestamp;
	private String path;

	public static <T> Builder<T> builder() {
		return new Builder<T>();
	}

	public static class Builder<T> {
		private HttpStatusResponse status;
		private T data;

		public Builder<T> status(HttpStatusResponse status) {
			this.status = status;
			return this;
		}

		public Builder<T> data(T data) {
			this.data = data;
			return this;
		}

		public ResponseBody<T> build() {
			HttpServletRequest servletRequest =
					((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
							.getRequest();
			String timestamp = DateTimeUtils.getCurrentDateString(DateTimeConstants.YYYY_MM_DD_HYPHEN);
			return new ResponseBody<T>(status, data, timestamp, servletRequest.getServletPath());
		}
	}
}
