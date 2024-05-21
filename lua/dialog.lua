local function init()
    local s = box.schema.create_space('dialog', {
        if_not_exists = true,
    })
    box.schema.sequence.create('id_seq',{ min=1, start=1, if_not_exists = true })
    s:format({
           { name = 'id', type = 'unsigned' },
           { name = 'key_id', type = 'string' },
           { name = 'from', type = 'unsigned' },
           { name = 'to', type = 'unsigned' },
           { name = 'text', type = 'string' }
    })
    s:create_index('primary', {
        type = 'tree',
        sequence = 'id_seq',
	    unique = true,
        parts = {'id'},
        if_not_exists = true,
    })

    s:create_index('secondary', {
        parts = {'key_id'},
        unique = false,
        if_not_exists = true
    })

end


function addDialog(from, to, text, keyId)
	local dialog = box.space.dialog
	return dialog:insert{nil, keyId, from, to, text}
end

function addDialogs(from, to, text, keyId)
	local dialog = box.space.dialog
	return dialog:insert{nil, keyId, from, to, text}
end

function getDialogs(keyId)
    local dialog = box.space.dialog
    return dialog.index.secondary:select{keyId}
end


init()
