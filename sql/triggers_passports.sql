/*passports licences triggers*/

---passport trigger for check number
DROP TRIGGER IF EXISTS date_passport_trigger ON public.passports;

CREATE OR REPLACE FUNCTION date_passport_function() RETURNS trigger AS $$
    begin
      IF new.date_in > new.date_out THEN
        RAISE EXCEPTION 'Дата выдочи не может быть больше даты действия';
      END IF;
    return new;
    end $$
LANGUAGE plpgsql;

CREATE TRIGGER date_passport_trigger
BEFORE INSERT ON passports
FOR EACH ROW EXECUTE PROCEDURE date_passport_function();

/*--------------------------------------------------------*/

-- trigger on the insert value number_passport = number_passport
DROP TRIGGER IF EXISTS passports_exists_trigger ON public.passports;

CREATE OR REPLACE FUNCTION passports_exists_function() RETURNS trigger AS  $$
    DECLARE passport_number text;
    begin
      passport_number := (SELECT number_passport FROM passports WHERE passports.number_passport = new.number_passport);

      IF new.number_passport = passport_number THEN
        UPDATE passports
        SET nationality     = new.nationality,
            department      = new.department,
            serial_passport = new.serial_passport,
            date_in         = new.date_in,
            date_out        = new.date_out,
            employee_id     = new.employee_id
        WHERE number_passport = new.number_passport;
        RAISE NOTICE '% and %', passport_number, new.number_passport;
        RETURN NULL;
      END IF;
    return new;
    end $$
LANGUAGE plpgsql;

CREATE TRIGGER passports_exists_trigger
BEFORE INSERT ON passports
FOR EACH ROW EXECUTE PROCEDURE passports_exists_function();

--test
INSERT INTO passports(nationality, department, serial_passport, number_passport, date_out, date_in, employee_id)
   VALUES('Belarus', 'dwdwwqd', 'MC', '15454544', '2000-12-20', '1999-12-20', 1);