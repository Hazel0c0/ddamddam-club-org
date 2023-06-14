// do some ajax, get whitelist array of all allowed tags, then set it onto the State
// set "showDropdown" to some value, which will filter the dropdown by that value
export const serverDelay = func => duration =>
    new Promise((resolve, reject) =>
        setTimeout(() => {
            resolve(func())
        }, duration || 1000)
    )

export const getWhitelistFromServer = serverDelay(() => [
    "aaa",
    "aaa1",
    "aaa2",
    "aaa3",
    "bbb1",
    "bbb2",
    "bbb3",
    "bbb4"
])

export const getValue = serverDelay(() => ["foo", "bar", "baz"])
