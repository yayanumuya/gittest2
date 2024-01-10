SELECT * FROM MEMBER;

CREATE SEQUENCE seq_test;
DECLARE
	v varchar2(20);
BEGIN
	FOR i IN 1..20000 loop
		SELECT 'comma_'||to_char(sysdate,'yymmdd')||'_'||lpad(seq_test.nextval,4,'0')
		INTO v
		FROM dual;
		DBMS_OUTPUT.PUT_LINE(v);
	END LOOP;
END;
SELECT * FROM memo;