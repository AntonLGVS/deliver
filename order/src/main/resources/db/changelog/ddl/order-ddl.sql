CREATE TABLE "delivery_order" (
   id UUID NOT NULL,
   state VARCHAR(255) NOT NULL,
   target_address VARCHAR(255) NOT NULL,
   comment VARCHAR(255),
   created_by VARCHAR(255) NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE,
   modified_by VARCHAR(255),
   modified_at TIMESTAMP WITHOUT TIME ZONE,
   CONSTRAINT pk_order PRIMARY KEY (id)
);