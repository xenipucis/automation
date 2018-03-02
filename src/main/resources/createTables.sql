create table test(
  id serial,
  test_name varchar(100) NOT NULL,
  base_uri varchar(100) NOT NULL,
  port int,
  relative_uri varchar(100) NOT NULL,
  http_method varchar(8) NOT NULL,
  body varchar(2500),
  test_validator_for_result varchar(2500) NOT NULL,
  expected_http_status int NOT NULL,
  processed boolean DEFAULT false,
  CONSTRAINT test_pkey PRIMARY KEY (id)
);

create table test_headers(
  test_id bigint NOT NULL,
  key varchar(100) NOT NULL,
  value varchar(100),
  is_request_header boolean DEFAULT true,
  CONSTRAINT test_headers_pkey PRIMARY KEY (test_id, key),
  CONSTRAINT test_headers_fk FOREIGN KEY (test_id)
  REFERENCES public.test (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO app;

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO app;