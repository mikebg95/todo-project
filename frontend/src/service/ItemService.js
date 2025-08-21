const apiPath = 'api/items';

export default {
   async getAllItems() {
       const res = await fetch(apiPath);
       if (!res.ok) throw new Error('Failed to fetch items: ' + res.status);
       return await res.json();
   },

   async addItem(text) {
       const res = await fetch(apiPath, {
           method: 'POST',
           headers: {
               'Content-Type': 'application/json'
           },
           body: JSON.stringify(text)
       })
       if (!res.ok) throw new Error(`POST ${res.status}`);
   },

    async deleteItem(id) {
        const res = await fetch(`${apiPath}/${encodeURIComponent(id)}`, { method: 'DELETE' });
        if (!res.ok) throw new Error(`DELETE ${res.status}`);
    },
}