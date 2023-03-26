CREATE TABLE "order" (
  created_by UUID NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE,
   modified_by UUID,
   modified_at TIMESTAMP WITHOUT TIME ZONE,
   id UUID NOT NULL,
   state VARCHAR(255),
   target_address VARCHAR(255) NOT NULL,
   comment VARCHAR(255),
   CONSTRAINT pk_order PRIMARY KEY (created_by, id)
);