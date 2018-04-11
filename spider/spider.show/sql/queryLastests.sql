SELECT 
  mi.`mv_id` AS mvId,
  mi.`name` AS mvName,
  mi.`player` AS player,
  mi.`poster` AS poster 
FROM
  t_mv_info mi 
ORDER BY mi.`created_time` DESC,
  mi.`mv_id` 
LIMIT 8 