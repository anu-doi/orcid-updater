DROP TABLE IF EXISTS publication_orcid;
DROP TABLE IF EXISTS publication_author;
DROP TABLE IF EXISTS publication;
DROP TABLE IF EXISTS email;
DROP TABLE IF EXISTS person;

CREATE TABLE person (
	id								bigserial
	, uid						varchar(10)
	, external_id			text
	, family_name		text
	, given_name		text
	, credit_name		text
	, description		text
	, orcid					text
	, PRIMARY KEY (id)
);
CREATE INDEX ON person (external_id);

CREATE TABLE email (
	id										bigserial
	, person_id					bigint
	, primary_address	boolean
	, email							varchar(255)
	, PRIMARY KEY (id)
	, CONSTRAINT fk_email_1 FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

CREATE TABLE publication (
	id										bigserial
	, external_id					text
	, title								text	not null
	, publication_name	text
	, publication_year		varchar(4)
	, work_type					text
	, issn								text
	, isbn								text
	, PRIMARY KEY (id)
);

CREATE TABLE publication_author (
	id								bigserial
	, external_id				text
	, publication_id		bigint
	, author_name			text
	, PRIMARY KEY (id)
	, CONSTRAINT fk_publication_author_1 FOREIGN KEY (publication_id) REFERENCES publication(id) ON DELETE CASCADE
	, CONSTRAINT u_publication_author_1 UNIQUE (external_id, publication_id)
);
CREATE INDEX ON publication_author (external_id);

CREATE TABLE publication_orcid (
	person_id			bigint
	, publication_id		bigint
	, PRIMARY KEY (person_id, publication_id)
	, CONSTRAINT fk_publication_orcid_1 FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
	, CONSTRAINT fk_publication_orcid_2 FOREIGN KEY (publication_id) REFERENCES publication(id) ON DELETE CASCADE
);

