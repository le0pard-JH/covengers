show user;

select * from tab;

select* from tbl_member;

describe TBL_LOGINHISTORY;

alter table KMS_TBL_LOGINHISTORY 
modify (CLIENTIP  VARCHAR2(200)); 
SELECT * FROM USER_TAB_COMMENTS


SELECT * FROM USER_COL_COMMENTS
SELECT * FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = '테이블명'

SELECT * FROM COLS WHERE TABLE_NAME = 'tbl_member';

create sequence seq_member_userno
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

CREATE table tbl_member
(userno            number  not null  -- 회원번호
,name             varchar2(30)   not null  -- 회원명
,email              varchar2(200)  not null  -- 이메일
,password      varchar2(200)  not null  -- 비밀번호 (SHA-256 암호화 대상)
,mobile             varchar2(200)            -- 연락처 (AES-256 암호화/복호화 대상) 
,postcode           varchar2(5)              -- 우편번호
,address            varchar2(200)            -- 주소
,detailAddress      varchar2(200)            -- 상세주소
,extraAddress       varchar2(200)            -- 참고항목
,gender             varchar2(1)              -- 성별 남1 여2 선택안함3
,birthday           varchar2(10)              -- 생년월일   
,taste                  varchar2(100)           --취향정보
,point              number         -- 포인트 
,registerday        date default sysdate     -- 가입일자 
,lastpwdchangedate  date default sysdate     -- 마지막으로 암호를 변경한 날짜  
,status             number(1) default 1 not null     -- 회원탈퇴유무   1: 사용가능(가입중) / 0:사용불능(탈퇴) 
,idle               number(1) default 0 not null     -- 휴면유무      0 : 활동중  /  1 : 휴면중 
,constraint PK_tbl_member_email primary key(email)
,constraint CK_tbl_member_gender check( gender in('1','2') )
,constraint CK_tbl_member_status check( status in(0,1) )
,constraint CK_tbl_member_idle check( idle in(0,1) )
);

commit;

SELECT to_number( regexp_replace( birthday, '[^0-9]+')) AS birthday, NAME FROM try_member_pjh;

select * from tbl_member;

drop TABLE try_member_pjh purge;

insert into try_member_pjh(userno, name, email, password, mobile, postcode, address, detailAddress, extraAddress, gender, birthday, taste, point) 
values(seq_member_userno.nextval, '박지현', 'parkjh@naver.com', 'qwer1234$', '01012334567', '12342' , '서오릉로15길' , '백현마을', '백현중학교', '2', '19960314', '대충,암거', '0');

select  * from TBL_CATEGORY_KIND;

delete from  try_member_ where name = '엄정화';


commit;

select * from tbl_member;

delete from tbl_member;

exec pcd_member_insert('유저');


create table tbl_loginhistory
(fk_userno      number not null
,logindate      date default sysdate not null
,clientip       varchar2(20) not null
,constraint FK_tbl_loginhistory foreign key(fk_userno) references tbl_member(userno)
);

CREATE OR REPLACE PROCEDURE pcd_member_insert (
    p_userno   IN   NUMBER,
    p_name     IN   VARCHAR2,
    p_gender   IN   VARCHAR2
) IS
BEGIN
    FOR i IN 1000..9999 LOOP
        INSERT INTO tbl_member (
            userno,
            name,
            email,
            password,
            mobile,
            postcode,
            address,
            detailaddress,
            extraaddress,
            gender,
            birthday,
            taste,
            point,
            registerday,
            lastpwdchangedate,
            status,
            idle
        ) VALUES (
            p_userno || i,
            p_name || i,
            'leess@gmail.com' || i,
            '18006e2ca1c2129392c66d87334bd2452c572058d406b4e85f43c1f72def10f5',
            'D5HhVhRHyeK3RNCaHP4dyg==',
            '04148',
            '서울 마포구 백범로 152',
            '102동 1501호',
            '  (공덕동, 공덕파크자이)',
            p_gender,
            '1996.07.16',
            '향수',
            '0',
            '2020-11-10',
            '2020-11-10',
            '1',
            '0'
        );

    END LOOP;
END pcd_member_insert;


exec pcd_member_insert(9000,'유저','1');
exec pcd_member_insert(1,'사람','2');
exec pcd_member_insert(2,'노네임','3');

delete from tbl_member where email like '%leess@gmail.com%';

select count (*) from tbl_member;

desc tbl_member;

select * from tbl_member;

select userno, email, name, birthday, gender, mobile, point, registerday, status, idle
from tbl_member
where userno != 0;

select *
from user_cons_columns
where table_name = 'TBL_member';
   
CREATE table tbl_shipping_test
(ship_seq           number  not null  -- 배송지번호
,fk_email              varchar2(200)  not null  -- 이메일
,receiverName      varchar2(30)  not null  -- 수취인명
,siteName             varchar2(200)  not null          --- 배송지이름
,postcode           varchar2(5)         not null     -- 우편번호
,address            varchar2(200)       not null     -- 주소
,detailAddress      varchar2(200)            -- 상세주소
,extraAddress       varchar2(200)            -- 참고항목
,mobile                 varchar2(200)    not null         -- 전화번호
,deliveryRequest       varchar2(500)           -- 배송요청사항
,status             number(1) not null     -- 기본배송지 유무   1: 기본배송지 / 0:그냥 배송지
, constraint FK_tbl_shipping_email foreign key(fk_email) references tbl_member(email)
,constraint CK_tbl_shipping_status check(status in(0,1) )
) tablespace users; 

create sequence seq_shipping
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


DROP TABLE tbl_shipping_test purge;

select * from TBL_SHIPPING_TEST;

insert into tbl_shipping_test(ship_seq, fk_email, receiverName, siteName, postcode, address, detailAddress, extraAddress, mobile, deliveryRequest, status) 
values (seq_shipping.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)

select * from tbl_shipping_test;

drop table tbl_shipping_test purge;

drop sequence seq_shipping;

delete from tbl_shipping_test;

delete from tbl_shipping_test where ship_seq = 4;
commit;

select ship_seq, siteName, status from tbl_shipping_test where fk_email = 'sjuACvVHK+mxovAapsCkf8YxvFzEAjVq7iKqYHkXTnI=' and status = 1;
update tbl_shipping_test  set status = 0  where ship_seq = 4;

select * from TBL_PRODUCT;

select * from tbl_payment_log;

--- 회원 주문 총 금액
select sum(total)  from tbl_payment_log where fk_userno = '2011131722';
--- member 에 insert

---  member (등급 생성) : 구매 금액별

--&nbsp; 1
--&ensp; 2
--&emsp; 3
--- 위에 3개는 ''

--< -- &lt;
--> --- &gt;

--null --- ''

--- / 상품별 판매 수량 /

--- 배송지 목록 / 

CREATE table tbl_shipping
(ship_seq           number  not null  -- 배송지번호
,fk_userno             varchar2(200)  not null  --회원번호
,receiverName      varchar2(30)  not null  -- 수취인명
,siteName             varchar2(200)  not null          --- 배송지이름
,postcode           varchar2(5)         not null     -- 우편번호
,address            varchar2(200)       not null     -- 주소
,detailAddress      varchar2(200)            -- 상세주소
,extraAddress       varchar2(200)            -- 참고항목
,mobile                 varchar2(200)    not null         -- 전화번호
,deliveryRequest       varchar2(500)           -- 배송요청사항
,status             number(1) not null     -- 기본배송지 유무   1: 기본배송지 / 0:그냥 배송지
, constraint FK_tbl_shipping_userno foreign key(fk_userno) references tbl_member(userno)
,constraint CK_tbl_shipping_status check(status in(0,1) )
) tablespace users; 

commit;

desc tbl_member;

select * from tbl_member;
select * from tbl_shipping;

alter table tbl_member add grade varchar(30) default 'Welcome' not null;
alter table tbl_member add column extraaddress;

alter table tbl_member drop column grade;
alter table tbl_member add extraaddress varchar2(200);
alter table tbl_member add grade varchar(30) default 'Welcome' not null;

commit;

select total, case when total between 300000 and 999999 then 'Bronze' when total between 1000000 and 4999999 then 'Silver' when total between 5000000 and 9999999 then 'GOLD' when total > 1000000 then 'Platinum' else 'Welcome' end as 등급
from (
select sum(total) as total from tbl_payment_log where fk_userno = '2011131722'
);

--- 제품별 판매량 (분기별)
select split_data, paydate, count(*) as 제품별분기별판매량
from
(
select trim(regexp_substr(product_code, '[^,]+', 1, level)) as split_data, paydate
    from (      
                select product_code as product_code, to_char(paydate, 'yyyy-q') as paydate
                from tbl_payment_log  )
                
connect by instr(product_code, ',', 1, level - 1) > 0
order by 1
)
group by split_data, paydate;

select split_data, paydate, count(*) as 제품별분기별판매량
from
(
select trim(regexp_substr(product_code, '[^,]+', 1, level)) as split_data, paydate
    from (      
                select product_code, to_char(paydate, 'yyyy-q') as paydate
                from tbl_payment_log  )
                
connect by instr(product_code, ',', 1, level - 1) > 0
order by 1
)
group by split_data, paydate;

select * from tbl_payment_log;

--- 제품별 판매량 (월별)
select split_data, paydate, count(*) as 제품별월별판매량
from
(
select trim(regexp_substr(product_code, '[^,]+', 1, level)) as split_data, paydate
    from (
                select product_code, to_char(paydate, 'yyyy-mm') as paydate
                from tbl_payment_log  )
                
connect by instr(product_code, ',', 1, level - 1) > 0
order by 1
)
group by split_data, paydate
having paydate = '2020-11'
order by 3 desc;

select kind, split_data, paydate, count(*) as 제품별월별판매량
from
(
select substr(product_code, 1,2) as kind , trim(regexp_substr(product_code, '[^,]+', 1, level)) as split_data, paydate
    from (
                select product_code, to_char(paydate, 'yyyy-mm') as paydate
                from tbl_payment_log  )
                
connect by instr(product_code, ',', 1, level - 1) > 0
order by 1
)
group by kind, split_data, paydate
having paydate = '2020-11' and kind = 'DF'
order by  4 desc;

select kind, split_data, paydate, count(*) as 제품별월별판매량
from
(
select substr(product_code, 1,2) as kind , trim(regexp_substr(product_code, '[^,]+', 1, level)) as split_data, paydate
    from (
                select product_code, to_char(paydate, 'yyyy-mm') as paydate
                from tbl_payment_log  )
                
connect by instr(product_code, ',', 1, level - 1) > 0
order by 1
)
group by kind, split_data, paydate
having paydate = to_char(sysdate,'yyyy-mm') and kind = 'PF'
order by  4 desc;

select * from tbl_product;

select * from tbl_member;
select * from tbl_shipping;

select *
from tbl_member M, tbl_shipping S
where M.userno = S.fk_userno;


SELECT userno, name, email, mobile, postcode, address, detailaddress, extraaddress, gender, 
birthday, taste, point, registerday, pwdchangegap,
nvl(lastlogingap, trunc( months_between(sysdate, registerday) ) ) AS lastlogingap 
FROM 
(
select userno, name, email, mobile, postcode, address, detailaddress, extraaddress, gender 
     , birthday, taste, point
     , to_char(registerday, 'yyyy-mm-dd') AS registerday 
     , trunc( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap 
from tbl_member 
where status = 1 and email = ? and password = ?
) M ;
CROSS JOIN
(
select trunc( months_between(sysdate, max(logindate)) ) AS lastlogingap 
from TBL_LOGINHISTORY 
where fk_userno = ? 
) H

select fk_userno from tbl_shipping where ship_seq = 15;

desc tbl_product;

select * from tbl_product 
where fk_kindcode = 'IS';

select enproductname, productimg1, productdescshort from tbl_product 
where  productcode = 'PF-101-002' ;

SELECT
    *
FROM
tab;

select * from tbl_kind;

CREATE table tbl_adproduct
(fk_productcode varchar2(30) not null
, constraint FK_tbl_adproduct_productcode foreign key(fk_productcode) references tbl_product(productcode)
, constraint UQ_tbl_adproduct_productcode unique(fk_productcode)
) tablespace users; 

drop table tbl_adproduct purge;

delete tbl_adproduct;
desc tbl_adproduct;
alter table tbl_adproduct modify fk_productcode varchar2(30) not null;
alter table tbl_adproduct add constraint FK_tbl_adproduct_kindcode foreign key(fk_kindcode) reference tbl_product(fk_kindcode);
alter table tbl_adproduct add fk_kindcode varchar2(30)  NOT NULL;
insert into tbl_adproduct (fk_productcode) values ('DF-201-003');
insert into tbl_adproduct (fk_productcode) values ('PF-101-002');
insert into tbl_adproduct (fk_productcode) values ('IS-301-002');
commit;
delete tbl_adproduct;


select * from tbl_adproduct where substr(fk_productcode,1,2) = 'PF';
desc tbl_adproduct;
commit;

desc tbl_product;
select enproductname, krproductname, productimg1, productimg2, productdescshort  from tbl_product where productcode = 'PF-101-002' ;

select fk_productcode from tbl_adproduct

select * from tbl_product;

select * from tab;

select * from TBL_CATEGORY where fk_kindcode = 'PF';

select categorycode, fk_kindcode, encategoryname, krcategoryname from tbl_category order by 1;