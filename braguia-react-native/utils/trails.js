export function getMedia(route) {
  const media = [];
  for (let i = 0; i < route.length; i++) {
    if (route[i].media) {
      media.push(...route[i].media);
    }
  }
  return media;
}

export function calculateRoute(edges) {
  const route = [];
  route.push(edges[0].edge_start);
  for (let i = 1; i < edges.length; i++) {
    route.push(edges[i].edge_start);
  }
  const lastEdge = edges[edges.length - 1].edge_end;
  route.push(lastEdge);
  return route;
}

export function formatDate(timestamp) {
  const date = new Date(timestamp);

  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are zero-based
  const year = date.getFullYear();

  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");

  return day + "-" + month + "-" + year + " " + hours + ":" + minutes;
}
