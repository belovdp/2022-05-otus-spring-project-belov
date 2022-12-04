export class TreeUtils {

    static findNodeById<T extends TreeItem<T>>(nodes: T[], id: number | string): T | null {
        for (const node of nodes) {
            if (String(node.id) === String(id)) {
                return node;
            }
            if (node.childs) {
                const resultFromChilds = this.findNodeById(node.childs, id);
                if (resultFromChilds) {
                    return resultFromChilds;
                }
            }
        }
        return null;
    }

    static disableNode<T extends TreeItem<T>>(nodes: (T & {disabled?: boolean})[]) {
        nodes.forEach(category => {
            category.disabled = true;
            if (category.childs) {
                this.disableNode(category.childs);
            }
        });
    }
}

type TreeItem<T> = {
    id: number | string;
    childs: T[];
}