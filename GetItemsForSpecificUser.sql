select 	users.ID AS 'ID',users.username AS 'Username',items.name AS 'Item Name',items.quantity AS 'Quantity',items.size AS 'Size (Weight)' FROM
	users
INNER JOIN 
	items 
ON
	users.ID = items.userid
WHERE
	users.ID = 2;