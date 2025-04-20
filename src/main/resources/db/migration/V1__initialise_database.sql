-- public 스키마가 없을 경우 생성
CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL CHECK (birth_date < CURRENT_DATE) -- 생년월일이 현재 날짜보다 과거여야 함
);

CREATE TABLE public.books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price INT NOT NULL CHECK (price >= 0), -- 가격이 0 이상이어야 함
    status VARCHAR(50) NOT NULL CHECK (status IN ('未出版', '出版済み')) -- 출판 상태 제약 조건
);

CREATE TABLE public.book_author (
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

-- 출판 상태 변경 제약을 위한 트리거
CREATE OR REPLACE FUNCTION prevent_status_downgrade()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.status = '出版済み' AND NEW.status = '未出版' THEN
        RAISE EXCEPTION '出版済みの書籍は未出版に変更できません。';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER status_change_trigger
BEFORE UPDATE ON books
FOR EACH ROW
EXECUTE FUNCTION prevent_status_downgrade();