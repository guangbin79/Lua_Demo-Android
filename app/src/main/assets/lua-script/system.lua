if (not system) then
  if (not (CachesDirectory and DocumentsDirectory and ResourceDirectory and TemporaryDirectory)) then
    return nil
  end

  local caches_path = CachesDirectory.."/lua-script.zip$?.lua;"
  local documents_path = DocumentsDirectory.."/lua-script.zip$?.lua;"
  local resource_path = ResourceDirectory.."$assets/lua-script/?.lua;"
  package.path = caches_path..documents_path..resource_path

  print("### CachesDirectory = "..CachesDirectory)
  print("### DocumentsDirectory = "..DocumentsDirectory)
  print("### ResourceDirectory = "..ResourceDirectory)
  print("### TemporaryDirectory = "..TemporaryDirectory)
  print("### package.path = "..package.path)
end

return require("system.config")
