
-- check in front end: src/actions/productAction.js method: productTypeList
INSERT INTO public.producttype (producttypeid, name) VALUES
    (1, 'House'),
    (2, 'Apartment'),
	(3, 'Land')
on conflict do nothing;