system = {}
local _M = system

_M.CachesDirectory = CachesDirectory
_M.DocumentsDirectory = DocumentsDirectory
_M.ResourceDirectory = ResourceDirectory
_M.TemporaryDirectory = TemporaryDirectory

CachesDirectory = nil
DocumentsDirectory = nil
ResourceDirectory = nil
TemporaryDirectory = nil

return _M
