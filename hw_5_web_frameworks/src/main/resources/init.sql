INSERT INTO Department (DepId, DepName, DepCity) VALUES (1, 'Administration', 'Moscow');
INSERT INTO Department (DepId, DepName, DepCity) VALUES (2, 'Marketing', 'Saint-Petersburg');
INSERT INTO Department (DepId, DepName, DepCity) VALUES (3, 'Purchasing', 'London');
INSERT INTO Department (DepId, DepName, DepCity) VALUES (4, 'Shipping', 'Hamburg');
INSERT INTO Department (DepId, DepName, DepCity) VALUES (5, 'Public Relations', 'New-York');

INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (1, 'SKING', 'sking', 'CEO');
INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (2, 'NKOCHHAR', 'nkochhar', 'ACC');
INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (3, 'LDEHAAN', 'ldehaan', 'HRM');
INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (4, 'AHUNOLD', 'ahunold', 'USR');
INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (5, 'BERNST', 'bernst', 'USR');
INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (6, 'DAUSTIN', 'daustin', 'ACC');
INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (7, 'VPATABAL', 'vpatabal', 'USR');
INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (8, 'DLORENTZ', 'dlorentz', 'USR');
INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (9, 'DFAVIET', 'dfaviet', 'USR');
INSERT INTO Account (AccId, AccUsername, AccPassword, AccRole) VALUES (10, 'ISCIARRA', 'isciarra', 'USR');

INSERT INTO Employee (EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) VALUES (1, 'Steven King', 's.king@company.com', '+7(234)231-12-12', '17-06-2011', 1, 'CEO', 24000, 1);
INSERT INTO Employee (EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) VALUES (2, 'Neena Kochhar', 'n.kochhar@company.com', '+7(234)231-12-13', '21-09-2009', 1, 'ACM', 17000, 2);
INSERT INTO Employee (EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) VALUES (3, 'Lex De Haan', 'l.dehaan@company.com', '+7(234)231-12-15', '13-01-2009', 1, 'AD_VP', 16000, 3);
INSERT INTO Employee (EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) VALUES (4, 'Alexander Hunold', 'a.hunold@company.com', '+7(456)231-12-12', '03-01-2014', 2, 'IT_PROG', 9000, 4);
INSERT INTO Employee (EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) VALUES (5, 'Bruce Ernst', 'b.ernst@company.com', '+1(122)231-12-10', '21-05-2015', 5, 'IT_PROG', 6000, 5);
INSERT INTO Employee (EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) VALUES (6, 'David Austin', 'd.austin@company.com', '+3(122)445-00-11', '25-06-2013', 3, 'FI_MGR', 12000, 6);
INSERT INTO Employee (EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) VALUES (7, 'Valli Pataballa', 'v.pataballa@company.com', '+3(122)445-00-12', '05-02-2014', 3, 'IT_PROG', 4800, 7);
INSERT INTO Employee (EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) VALUES (8, 'Diana Lorentz', 'd.lorentz@company.com', '+3(122)445-00-13', '07-02-2015', 3, 'IT_PROG', 4200, 8);
INSERT INTO Employee (EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) VALUES (9, 'Daniel Faviet', 'd.faviet@company.com', '+5(252)631-42-55', '16-08-2010', 4, 'PU_MAN', 2800, 9);
INSERT INTO Employee (EmpId, EmpName, EmpEmail, EmpPhone, EmpHireDate, EmpDepId, EmpJob, EmpSalary, EmpAccId) VALUES (10, 'Ismael Sciarra', 'i.sciarra@company.com', '+5(252)631-42-56', '30-09-2013', 4, 'PU_MAN', 2700, 10);