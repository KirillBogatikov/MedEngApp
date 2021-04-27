 INSERT INTO "order" ("id","customer") VALUES
	 ('6f70dd43-1b8c-46be-a40b-2fa55ae613c1','8cbf31b1-8a85-4e59-bfe0-0b9dc016c578'),
	 ('bfc26d2f-97af-46ab-a421-4c9e10534366','2622dbab-b481-4c6a-a078-22434f86772b'),
	 ('e7d6d240-bdb7-4614-bf4a-c17fc85f50d5','230b64b5-84ea-4d93-a602-843946c45472'),
	 ('660eaeeb-0ac4-428d-98ad-f666f1338820','9d387aef-6fe9-439d-81c4-e52b8361bcab');
INSERT INTO "operation" ("id","product","count","type","date") VALUES
	 ('859f1528-eed9-4d8f-97c0-e8f92e56ce9f','3d6688ee-9579-493f-b86f-04ff7f688747',6,'Booking','2021-04-20 12:21:48'),
	 ('9fd7aa64-0125-4976-af83-3497062365d9','9356561c-e8ad-487b-9969-b7353e0f3e3c',3,'Booking','2021-04-20 16:41:18'),
	 ('f5d9dede-2f3c-4d2b-89d6-a09aaf0e180a','9356561c-e8ad-487b-9969-b7353e0f3e3c',10,'Shipment','2021-04-23 14:11:08'),
	 ('657e3d41-43e9-4803-8b60-7d1ff67cd242','5035afca-136b-4966-8f7c-b3d5927b4063',8,'Booking','2021-04-25 21:47:12'),
	 ('d86191b9-9436-478d-8585-d4f21df5313a','d0b07d53-3dac-4ad7-9f8e-960485b784e4',1,'Booking','2021-04-18 12:20:40'),
	 ('02048e4a-d0b8-4ecc-a1d8-13d2233ce720','d0b07d53-3dac-4ad7-9f8e-960485b784e4',3,'Shipment','2021-04-19 12:20:40'),
	 ('9b06a6de-da56-4f30-b953-2604e072eb5c','5035afca-136b-4966-8f7c-b3d5927b4063',12,'Shipment','2021-04-24 21:47:12');
INSERT INTO "order_item" ("id","order","operation") VALUES
	 ('73fac6dc-84be-4e79-9fe9-9b5a19af46e9','6f70dd43-1b8c-46be-a40b-2fa55ae613c1','859f1528-eed9-4d8f-97c0-e8f92e56ce9f'),
	 ('f0a0ff12-d448-4f24-8b81-b7f76989a7d5','bfc26d2f-97af-46ab-a421-4c9e10534366','9fd7aa64-0125-4976-af83-3497062365d9'),
	 ('2dba97c7-bf95-427f-9874-bd0c5194c52d','e7d6d240-bdb7-4614-bf4a-c17fc85f50d5','657e3d41-43e9-4803-8b60-7d1ff67cd242'),
	 ('b7d99e89-1e1b-40af-9a8c-d0a1e7986fd7','660eaeeb-0ac4-428d-98ad-f666f1338820','d86191b9-9436-478d-8585-d4f21df5313a');
 INSERT INTO "order_status_history" ("id","order","status","date") VALUES
	 ('53e3ad4d-2fe1-408f-8e74-b503a3ec5cf3','6f70dd43-1b8c-46be-a40b-2fa55ae613c1','Created','2021-04-20 12:21:48'),
	 ('d3090756-03eb-429d-aeaa-b0ad62e5ba60','6f70dd43-1b8c-46be-a40b-2fa55ae613c1','Processing','2021-04-20 14:21:48'),
	 ('13639372-1ee3-48a9-883b-a453ba123837','6f70dd43-1b8c-46be-a40b-2fa55ae613c1','Booked','2021-04-20 18:21:48'),
	 ('b198de1c-fc77-44b9-9aac-7f6e1c23dcbd','bfc26d2f-97af-46ab-a421-4c9e10534366','Created','2021-04-20 16:41:18'),
	 ('4e34ae5a-0a17-4305-b0ce-c9810d89f33f','e7d6d240-bdb7-4614-bf4a-c17fc85f50d5','Created','2021-04-25 21:47:12'),
	 ('16fb7c49-de9f-4875-bd17-572cc4e0489c','e7d6d240-bdb7-4614-bf4a-c17fc85f50d5','Processing','2021-04-26 08:47:12'),
	 ('f53de9ee-87d5-47b6-8d92-b55923a148ac','660eaeeb-0ac4-428d-98ad-f666f1338820','Created','2021-04-18 12:20:40');