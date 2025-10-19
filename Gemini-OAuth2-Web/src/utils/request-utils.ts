function getQueryString() {
  const hash = window.location.hash || '';
  const queryString = hash.includes('?') ? hash.split('?')[1] : '';
  return queryString;
}

function getRequestParam(name: string) {
  const queryString = getQueryString();
  const reg = new RegExp(`(^|&)${name}=([^&]*)(&|$)`);
  const result = queryString.match(reg);
  return result != null ? result[2] : '';
}

function getRequestParams() {
  const result: Record<string, string> = {};
  const queryString = getQueryString();
  const pairs = queryString !== '' ? queryString.split('&') : [];
  pairs.forEach((item) => {
    if (item) {
      const pair = item.split('=');
      result[pair[0]] = pair[1];
    }
  });
  return result;
}

export { getRequestParam, getRequestParams };
